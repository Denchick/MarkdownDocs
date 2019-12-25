import Document from "../models/Document";
import Cookies from "js-cookie";

export const createDocument = async () => {
    const response = await fetch(`/api/documents/`, {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json',
            userId: Cookies.get('userId') || '',
            auth: Cookies.get('auth') || ''
        },
        body: JSON.stringify(createDefaultDocument()),
	});
    return response.json();
};

export const getDocuments = async () => {
    const response = await fetch(`/api/documents/`, {
        headers: {
            userId: Cookies.get('userId') || '',
            auth: Cookies.get('auth') || ''
        }
    });
    return await response.json();
}

export const getDocument = async (documentId: string) => {    
    const response = await fetch(`/api/documents/${documentId}`, {
        headers: {
            userId: Cookies.get('userId') || '',
            auth: Cookies.get('auth') || ''
        }
    });
    return response.json();
}

export const updateDocument = async (document: Document) => {
    await fetch(`/api/documents/${document.metaInfo.id}/`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            userId: Cookies.get('userId') || '',
            auth: Cookies.get('auth') || ''
        },
        body: JSON.stringify(document),
	});
}

export const deleteDocument = async (documentId: string) => {
    await fetch(`/api/documents/${documentId}/`, {
        method: 'DELETE',
        headers: {
            userId: Cookies.get('userId') || '',
            auth: Cookies.get('auth') || ''
        }
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