import { Component } from "react";
import React from "react";
import { registerUser } from "../api/UsersApi";
import { Redirect } from "react-router-dom";
import { toast } from "react-toastify";

interface IRegisterPageState {
    isSuccessRegister: boolean
    login: string;
    password: string;
    timeToRedirect: boolean
}

export default class RegisterPage extends Component<{},IRegisterPageState> {
    constructor() {
        super({});
        this.state = { 
            login: '',
            password: '',
            isSuccessRegister: false,
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
        const isSuccess = await registerUser(this.state);
        if (!isSuccess) {
            toast.error("Something goes wrong");
        }
        this.setState({isSuccessRegister: isSuccess})
    }

    render() {
        if (this.state.timeToRedirect) {
            return <Redirect push to="/login" />
        }
        if (this.state.isSuccessRegister) {
            toast.info("Successs. Redirecting...");
            setTimeout(() => this.setState({timeToRedirect: true}), 3000);
        }
        return (
            <div style={{width: 400}}>
                <h2>Register</h2>
                <form className="pure-form">
                    <fieldset className="pure-group">
                        <input type="text" className="pure-input-1" placeholder="Login" onChange={this.handleLoginChange} />
                        <input type="password" className="pure-input-1" placeholder="Password" onChange={this.handlePasswordChange} />
                    </fieldset>
                    <button type="button" className="pure-button pure-input-1" onClick={this.handleSubmit}>Sign up!</button>
                </form>
                <p>Already have an account? <a href="/login">Login!</a></p>
            </div>
        );
    }
}