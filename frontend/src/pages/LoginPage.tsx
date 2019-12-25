import { Component } from "react";
import React from "react";
import { loginUser } from "../api/UsersApi";
import { Redirect } from "react-router-dom";

interface ILoginPageState {
    login: string;
    password: string;
    isSuccessLogin: boolean;
}

export default class LoginPage extends Component<{},ILoginPageState> {
    constructor() {
        super({});
        this.state = {
            login: '',
            password: '',
            isSuccessLogin: false
        }
    }
    handleLoginChange = (event:any) => {
        this.setState({login: event.target.value});
    }

    handlePasswordChange = (event: any) => {
        this.setState({password: event.target.value});
    }
    
    handleSubmit = async () => {
        const isSuccess = await loginUser(this.state);
        this.setState({isSuccessLogin: isSuccess})
    }

    render() {
        if (this.state.isSuccessLogin) {
            return <Redirect push to="/documents" />
        }
        return (
            <div style={{width: 400}}>
                <form className="pure-form">
                    <fieldset className="pure-group">
                        <input type="text" className="pure-input-1" placeholder="Login" onChange={this.handleLoginChange} />
                        <input type="text" className="pure-input-1" placeholder="Password" onChange={this.handleLoginChange} />
                    </fieldset>
                    <button type="submit" className="pure-button pure-input-1" onClick={this.handleSubmit}>Sign in</button>
                </form>
            </div>
        );
    }
}