import Document from "../domain/Document";
import MetaInfo from "../domain/MetaInfo";

export default class MockDocumentsApi {
    private document: Document = {
        metaInfo: {
            id: "00000000-0000-0000-0000-000000000000",
            author: "Denis",
            title: "Hello, world!",
            createdAt: Date.now(),
            editedAt: Date.now()
        },
        content: "This is **content**"
    };

    getDocuments(): Promise<MetaInfo[]> {
        console.log("GET DOCUMENTS REQUEST");
        return new Promise((resolve, reject) => { 
            setTimeout(() => { 
                resolve([this.document.metaInfo]); 
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
                resolve(this.document.metaInfo.id); 
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