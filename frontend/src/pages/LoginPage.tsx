import { Component } from "react";
import React from "react";
import { loginUser } from "../api/UsersApi";
import { Redirect } from "react-router-dom";
import { toast } from "react-toastify";

interface ILoginPageState {
    login: string;
    password: string;
    isSuccessLogin: boolean;
    timeToRedirect: boolean;
}

export default class LoginPage extends Component<{},ILoginPageState> {
    constructor() {
        super({});
        this.state = {
            login: '',
            password: '',
            isSuccessLogin: false,
            timeToRedirect: false
        }
    }
    handleLoginChange = (event:any) => {
        this.setState({login: event.target.value});
    }

    handlePasswordChange = (event: any) => {
        this.setState({password: event.target.value});
    }
    
    handleSubmit = async () => {
        if (!this.state.login || !this.state.password) {
            toast.error("Fill username and password fields");
            return;
        }
        const isSuccess = await loginUser(this.state);
        if (!isSuccess) {
            toast.error("Something goes wrong");
        }
        this.setState({isSuccessLogin: isSuccess})
    }

    render() {
        if (this.state.timeToRedirect) {
            return <Redirect push to="/login" />
        }
        if (this.state.isSuccessLogin) {
            toast.info("Successs. Redirecting...");
            setTimeout(() => this.setState({timeToRedirect: true}), 3000);
        }
        if (this.state.timeToRedirect) {
            return <Redirect to="/documents" />
        }
        return (
            <div style={{width: 400}}>
                <h2>Signing in</h2>
                <form className="pure-form">
                    <fieldset className="pure-group">
                        <input
                            type="text"
                            className="pure-input-1"
                            placeholder="Your Login"
                            onChange={this.handleLoginChange}
                        />
                        <input
                            type="password"
                            className="pure-input-1"
                            placeholder="Yout password"
                            onChange={this.handlePasswordChange}
                        />
                    </fieldset>
                    <button type="button" className="pure-button pure-input-1" onClick={this.handleSubmit}>Sign in!</button>
                </form>
                <p>Not registered yet? <a href="/register">Let's do this!</a></p>
            </div>
        );
    }
}