import { Component } from "react";
import React from "react";
import { registerUser } from "../api/UsersApi";

interface IRegisterPageState {
    isSuccessRegister: boolean
    login: string;
    password: string
}

export default class RegisterPage extends Component<{},IRegisterPageState> {
    constructor() {
        super({});
        this.state = { 
            login: '',
            password: '',
            isSuccessRegister: false
        }
    }
    handleLoginChange = (event:any) => {
        this.setState({login: event.target.value});
    }

    handlePasswordChange = (event: any) => {
        this.setState({password: event.target.value});
    }
    
    handleSubmit = async () => {
        const isSuccess = await registerUser(this.state);
        this.setState({isSuccessRegister: isSuccess})
    }

    render() {
        if (this.state.isSuccessRegister) {
            
        }
        return (
            <div style={{width: 400}}>
                <form className="pure-form">
                    <fieldset className="pure-group">
                        <input type="text" className="pure-input-1" placeholder="Login" onChange={this.handleLoginChange} />
                        <input type="text" className="pure-input-1" placeholder="Password" onChange={this.handleLoginChange} />
                    </fieldset>
                    <button type="submit" className="pure-button pure-input-1">Sign in</button>
                </form>
            </div>
        );
    }
}