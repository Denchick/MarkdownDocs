import Cookies from "js-cookie"

export const toShareDocument = async (documentId: string) => {
    const response = await fetch(`/api/share/${documentId}/`, {
        method: 'POST',
        headers: {
            userId: Cookies.get('userId') || '',
            auth: Cookies.get('auth') || ''
        }
    });
    return response;
}

export const getSharedDocumentContent = async (shareToken: string) => {
    const response = await fetch(`/api/share/${shareToken}/`, {
        headers: {
            userId: Cookies.get('userId') || '',
            auth: Cookies.get('auth') || ''
        }
    });
    return response.text();
}