import UserCredentials from "../models/UserCredentials"
import LoginUserResponse from "../models/LoginUserResponse"
import Cookies from 'js-cookie'


export const loginUser = async (userCredentials: UserCredentials): Promise<boolean> => {
    const response = await fetch(`/api/users/${userCredentials.login}/`, {
        method: 'POST',
        body: userCredentials.password
    })
    console.log(response);
    if (response.ok) {
        const {userId, auth} = response.json() as unknown as LoginUserResponse;
        Cookies.set('userId', userId)
        Cookies.set('auth', auth)
        
        return true;
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