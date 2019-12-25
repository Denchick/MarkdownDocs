import UserCredentials from "../models/UserCredentials"
import LoginUserResponse from "../models/LoginUserResponse"
import Cookies from 'js-cookie'


export const loginUser = async (userCredentials: UserCredentials): Promise<boolean> => {
    const response = await fetch(`/api/users/${userCredentials.login}/`, {
        body: userCredentials.password
    })
    
    if (response.ok) {
        const {userId, Auth} = response.json() as unknown as LoginUserResponse;
        Cookies.set('userId', userId)
        Cookies.set('Auth', Auth)
    }

    return false;
}

export const registerUser = async (userCredentials: UserCredentials): Promise<boolean> => {
    const response = await fetch(`/api/users/`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userCredentials)
    })
    return response.ok;
}