import UserCredentials from "../models/UserCredentials"
import LoginUserResponse from "../models/LoginUserResponse"


export const loginUser = async (userCredentials: UserCredentials): Promise<boolean> => {
    const response = await fetch(`/api/users/`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userCredentials)
    })
    
    if (response.ok) {
        const {userId, Auth} = response.json() as unknown as LoginUserResponse;
        document.cookie = `userId=${userId};Auth=${Auth}`;
    }

    return response.ok;
}

export const registerUser = async (userCredentials: UserCredentials): Promise<boolean> => {
    const response = await fetch(`/api/users/`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userCredentials)
    })
    return response.ok;
}