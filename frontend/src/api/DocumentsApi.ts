import Document from "../models/Document";

export const createDocument = async () => {
    const response = await fetch(`/api/documents/`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(createDefaultDocument()),
	});
    return response.json();
};

export const getDocuments = async () => {    
    const response = await fetch(`/api/documents/`)
    return response.json();
}

export const getDocument = async (documentId: string) => {    
    const response = await fetch(`/api/documents/${documentId}`);
    return response.json();
}

export const updateDocument = async (document: Document) => {
    await fetch(`/api/documents/${document.metaInfo.id}/`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(document),
	});
}

export const deleteDocument = async (documentId: string) => {
    await fetch(`/api/documents/${documentId}/`, {
        method: 'DELETE'
	});
}

const createDefaultDocument = () => {
    return {
        metaInfo: {
            title: 'New document',
            author: 'User'
        },
        content:'## Hello world!'
    };
};