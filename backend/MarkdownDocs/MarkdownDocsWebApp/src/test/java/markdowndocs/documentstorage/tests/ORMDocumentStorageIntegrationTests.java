package markdowndocs.documentstorage.tests;

import markdowndocs.OrmPersistents.DocumentEntity;
import markdowndocs.documentstorage.*;
import markdowndocs.infrastructure.Result;
import markdowndocs.infrastructure.ValueResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ORMDocumentStorageIntegrationTests {

    @Mock
    private IQueryExecutor queryExecutor;

    @Rule
    public MockitoRule mockitoRule;


    @Before
    public void Before() {
        mockitoRule = MockitoJUnit.rule();
        queryExecutor = mock(IQueryExecutor.class);
    }

    @Test
    public void Should_success_create_document_and_return_id_of_new_document() throws Exception {

        String title = "title";
        String content = "content";
        UUID userId = UUID.randomUUID();
        when(queryExecutor.EntityExist(any())).thenReturn(false);
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());
        ArgumentCaptor<DocumentEntity> entityCaptor = ArgumentCaptor.forClass(DocumentEntity.class);
        ValueResult<UUID, String> result = documentStorage.CreateDocument(title, content, userId);

        verify(queryExecutor).Create(entityCaptor.capture());
        final DocumentEntity createdEntity = entityCaptor.getValue();
        assertTrue(result.isSuccess());
        assertNotNull(result.getValue());
        assertEquals(title, createdEntity.getTitle());
        assertEquals(content, createdEntity.getContent());
    }

    @Test
    public void Should_create_filed_when_query_executor_throw_exception() throws Exception {

        when(queryExecutor.EntityExist(any())).thenReturn(false);
        doThrow(new Exception("some db error")).when(queryExecutor).Create(any());
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        ValueResult<UUID, String> result = documentStorage.CreateDocument("title", "content", UUID.randomUUID());
        assertFalse(result.isSuccess());
    }

    @Test
    public void Should_create_failed_when_document_with_generated_id_already_exist() {
        when(queryExecutor.EntityExist(any())).thenReturn(true);
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        ValueResult<UUID, String> result = documentStorage.CreateDocument("title", "content", UUID.randomUUID());

        assertFalse(result.isSuccess());

    }

    private Logger CreateLogger() {
        return Logger.getLogger(ORMDocumentStorageIntegrationTests.class.getName());
    }

    @Test
    public void Should_call_update_query_executor_when_storage_update_document() throws Exception {

        DocumentEntity storedDocument = StorageEntityConverter.DocumentToDbEntity(CreateDocument(), UUID.randomUUID());
        when(queryExecutor.GetEntityBy(any(), eq(DocumentEntity.class))).thenReturn(storedDocument);
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());
        String newTitle = "new title";
        String newContent = "new content";

        ArgumentCaptor<DocumentEntity> entityCaptor = ArgumentCaptor.forClass(DocumentEntity.class);

        Result<DocumentStorageError> result = documentStorage.UpdateDocument(newTitle,
                newContent, storedDocument.getId());

        verify(queryExecutor).Update(entityCaptor.capture());
        final DocumentEntity updatedEntity = entityCaptor.getValue();
        assertTrue(result.isSuccess());
        assertEquals(newTitle, updatedEntity.getTitle());
        assertEquals(newContent, updatedEntity.getContent());
    }

    @Test
    public void Should_update_failed_when_query_executor_throw_exception() throws Exception {
        Document testDocument = CreateDocument();
        when(queryExecutor.GetEntityBy(any(), eq(DocumentEntity.class))).thenReturn(StorageEntityConverter.DocumentToDbEntity(testDocument, UUID.randomUUID()));
        doThrow(new Exception("some db error")).when(queryExecutor).Update(any());
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());


        Result<DocumentStorageError> result = documentStorage.UpdateDocument(testDocument.getMetaInfo().getTitle(),
                testDocument.getContent(), testDocument.getMetaInfo().getId());

        assertFalse(result.isSuccess());
        assertEquals(result.getError(), DocumentStorageError.UnknownError);
    }

    @Test
    public void Should_update_failed_when_storage_not_contain_document() {

        when(queryExecutor.EntityExist(any())).thenReturn(false);
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());
        Document testDocument = CreateDocument();

        Result<DocumentStorageError> result = documentStorage.UpdateDocument(testDocument.getMetaInfo().getTitle(),
                testDocument.getContent(), testDocument.getMetaInfo().getId());

        assertFalse(result.isSuccess());
        assertEquals(result.getError(), DocumentStorageError.NotFound);
    }

    @Test
    public void Should_get_document_from_storage() throws Exception {
        Document testDocument = CreateDocument();
        when(queryExecutor.GetEntityBy(any(), eq(DocumentEntity.class)))
                .thenReturn(StorageEntityConverter.DocumentToDbEntity(testDocument, UUID.randomUUID()));
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        ValueResult<Document, DocumentStorageError> result = documentStorage.GetDocument(testDocument.getMetaInfo().getId());

        assertTrue(result.isSuccess());
        assertEquals(result.getValue(), testDocument);
    }

    @Test
    public void Should_get_fail_when_query_executor_throw_exception() throws Exception {

        Document testDocument = CreateDocument();
        doThrow(new Exception("some db error")).when(queryExecutor).GetEntityBy(any(), eq(DocumentEntity.class));
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        ValueResult<Document, DocumentStorageError> result = documentStorage.GetDocument(testDocument.getMetaInfo().getId());

        assertFalse(result.isSuccess());
        assertEquals(result.getError(), DocumentStorageError.UnknownError);
    }

    @Test
    public void Should_get_fail_when_document_not_contain_in_storage() throws Exception {
        Document testDocument = CreateDocument();
        when(queryExecutor.GetEntityBy(any(), eq(DocumentEntity.class))).thenReturn(null);
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        ValueResult<Document, DocumentStorageError> result = documentStorage.GetDocument(testDocument.getMetaInfo().getId());

        assertFalse(result.isSuccess());
        assertEquals(result.getError(), DocumentStorageError.NotFound);
    }

    @Test
    public void Should_return_all_metas_for_user() throws Exception {
        UUID ownerId = UUID.randomUUID();
        Document document1 = CreateDocument();
        Document document2 = CreateDocument();
        ArrayList<DocumentEntity> storedEntity = new ArrayList<DocumentEntity>();
        storedEntity.add(StorageEntityConverter.DocumentToDbEntity(document1, ownerId));
        storedEntity.add(StorageEntityConverter.DocumentToDbEntity(document2, ownerId));
        when(queryExecutor.GetMetaInfoBy(ownerId)).thenReturn(storedEntity);


        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        ValueResult<Collection<MetaInfo>, String> result = documentStorage.GetDocumentInfos(ownerId);

        assertTrue(result.isSuccess());
        assertTrue(result.getValue().contains(document1.getMetaInfo()));
        assertTrue(result.getValue().contains(document2.getMetaInfo()));
    }

    @Test
    public void Should_get_empty_info_collection_when_storage_not_contain_document_for_user() throws Exception {
        UUID ownerId = UUID.randomUUID();

        ArrayList<DocumentEntity> storedEntity = new ArrayList<>();
        when(queryExecutor.GetMetaInfoBy(ownerId)).thenReturn(storedEntity);


        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        ValueResult<Collection<MetaInfo>, String> result = documentStorage.GetDocumentInfos(ownerId);

        assertTrue(result.isSuccess());
        assertTrue(result.getValue().isEmpty());
    }

    @Test
    public void Should_get_info_fail_when_query_executor_throw_error() throws Exception {
        UUID ownerId = UUID.randomUUID();
        doThrow(new Exception("some db error")).when(queryExecutor).GetMetaInfoBy(any());

        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        ValueResult<Collection<MetaInfo>, String> result = documentStorage.GetDocumentInfos(ownerId);

        assertFalse(result.isSuccess());
    }

    @Test
    public void Should_call_query_executor_delete_when_delete_document_from_storage() throws Exception {
        when(queryExecutor.EntityExist(any())).thenReturn(true);
        ArgumentCaptor<UUID> entityCaptor = ArgumentCaptor.forClass(UUID.class);
        UUID id = UUID.randomUUID();
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        Result<DocumentStorageError> result = documentStorage.DeleteDocument(id);

        verify(queryExecutor).DeleteById(entityCaptor.capture());
        final UUID deletedId = entityCaptor.getValue();
        assertTrue(result.isSuccess());
        assertEquals(deletedId, id);
    }

    @Test
    public void Should_delete_fail_when_query_executor_throw_exception() throws Exception {
        UUID id = UUID.randomUUID();
        when(queryExecutor.EntityExist(any())).thenReturn(true);
        doThrow(new Exception("some db error")).when(queryExecutor).DeleteById(id);
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        Result<DocumentStorageError> result = documentStorage.DeleteDocument(id);

        assertFalse(result.isSuccess());
        assertEquals(result.getError(), DocumentStorageError.UnknownError);
    }

    @Test
    public void Should_delete_fail_when_storage_not_contain_document() {
        UUID id = UUID.randomUUID();
        when(queryExecutor.EntityExist(id)).thenReturn(false);
        IDocumentStorage documentStorage = new DocumentStorage(queryExecutor, CreateLogger());

        Result<DocumentStorageError> result = documentStorage.DeleteDocument(id);

        assertFalse(result.isSuccess());
        assertEquals(result.getError(), DocumentStorageError.NotFound);
    }


    private Document CreateDocument() {
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setId(UUID.randomUUID());
        metaInfo.setTitle("Title");
        metaInfo.setCreateAt(new Timestamp(System.currentTimeMillis()));
        metaInfo.setEditedAt(new Timestamp(System.currentTimeMillis()));

        return new Document(metaInfo, "content");
    }


    @After
    public void after() {
    }
}
