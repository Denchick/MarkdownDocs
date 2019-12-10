import Document from "../domain/Document";

export default class MockDocumentsApi {
    private document: Document = {
        metaInfo: {
            documentId: "f63e3113-9583-47ee-9a8e-809f9077e6f7",
            author: "Denis",
            title: "Hello, world!",
            createdAt: new Date(),
            updatedAt: new Date()
        },
        content: "This is **content**"
    };

    getDocuments(): Promise<Document[]> {
        console.log("GET DOCUMENTS REQUEST");
        return new Promise((resolve, reject) => { 
            setTimeout(() => { 
                resolve([this.document]); 
            }, 2000); 
        }); 
    }

    getDocument(documentId: string): Promise<Document> {
        console.log(`GET DOCUMENT ${documentId} REQUEST`);
        return new Promise((resolve, reject) => { 
            setTimeout(() => { 
                resolve(this.document); 
            }, 2000); 
        }); 
    }

    createDocument(document: Document): Promise<string> {
        console.log(`CREATE DOCUMENT REQUEST`);
        return new Promise((resolve, reject) => { 
            setTimeout(() => { 
                resolve(this.document.metaInfo.documentId); 
            }, 2000); 
        });
    }

    updateDocument(document: Document): Promise<void> {
        console.log(`UPDATE DOCUMENT REQUEST`);
        return new Promise((resolve, reject) => { 
            setTimeout(() => { 
                resolve(); 
            }, 2000); 
        });
    }
    
    deleteDocument(documentId: string): Promise<void> {
        console.log(`DELETE DOCUMENT REQUEST`);
        return new Promise((resolve, reject) => { 
            setTimeout(() => { 
                resolve(); 
            }, 2000); 
        });
    }



}