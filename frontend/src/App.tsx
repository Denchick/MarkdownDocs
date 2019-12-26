import { Component } from "react";
import { RouteComponentProps, Switch, Route, Redirect, BrowserRouter } from "react-router-dom";
import React from "react";
import SharedDocumentPage from "./pages/SharedDocumentPage";
import { getDocuments } from "./api/DocumentsApi";
import DocumentsPage from "./pages/DocumentsPage";
import EditorPage from "./pages/EditorPage";
import LoginPage from "./pages/LoginPage";
import { ToastContainer } from "react-toastify";
import RegisterPage from "./pages/RegisterPage";
import { AuthorizedContext } from "./utils/AuthorizedContext";
import { isAuthorized } from "./utils/common";
import Header from "./components/Header";

interface IAppState {
  isAuthorized: boolean;
}

interface DocumentMatchParams {
  documentId: string;
}

interface ShareMatchParams {
  token: string;
}

interface DocumentMatchProps extends RouteComponentProps<DocumentMatchParams> { }

interface ShareMatchProps extends RouteComponentProps<ShareMatchParams> { }

export default class App extends Component<{}, IAppState> {
    constructor(props: any) {
        super(props);
        this.state = {
          isAuthorized: !!isAuthorized(),
        }
        
    }

    renderSwitch() {
        if (this.state.isAuthorized) {
          return (
            <Switch>
                <Route path="/share/:token" component={( {match}: ShareMatchProps) => (
                  <SharedDocumentPage token={match.params.token} />  )} />
                <Route path="/documents/:documentId" component={( {match}: DocumentMatchProps) => (
                  <EditorPage documentId={match.params.documentId} /> )}/>
                <Route path="/documents" component={() => <DocumentsPage getDocuments={getDocuments}/>} />
                <Redirect to="/documents" />
            </Switch>
          );
        }
      
        return (
          <Switch>
            <Route path="/share/:token" component={( {match}: ShareMatchProps) => (
              <SharedDocumentPage token={match.params.token} />  )} />
            <Route path="/login" component={LoginPage} />
            <Route path="/register" component={RegisterPage} />
            <Redirect to="/login" />
          </Switch>
        );
      }

    render() {
        return (
            <AuthorizedContext.Provider value={
              {
                isAuthorized: this.state.isAuthorized,
                changeValue: (value: boolean) => this.setState({isAuthorized: value})}
              }
            >
              <Header />
              <div className="container" >
                  <div className="demo">
                    <BrowserRouter>{this.renderSwitch()}</BrowserRouter>
                    <ToastContainer closeOnClick hideProgressBar autoClose={3000} position="bottom-center"/>
                  </div>
              </div>
            </AuthorizedContext.Provider>
          );
    }
}