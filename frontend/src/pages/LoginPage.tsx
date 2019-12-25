import { Component } from "react";
import React from "react";

interface ILoginPageState {
    login: string;
    password: string
}

export default class LoginPage extends Component<{},ILoginPageState> {
    constructor() {
        super({});
        this.state = {
            login: '',
            password: ''
        }
    }
    handleLoginChange = (event:any) => {
        this.setState({login: event.target.value});
    }

    handlePasswordChange = (event: any) => {
        this.setState({password: event.target.value});
    }
    
    render() {
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