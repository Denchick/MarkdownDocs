import { Component } from "react";
import { RouteComponentProps, Switch, Route, Redirect, BrowserRouter } from "react-router-dom";
import React from "react";
import SharedDocumentPage from "./pages/SharedDocumentPage";
import sampleMarkdown from "./sampleMarkdown";
import { getDocuments } from "./api/DocumentsApi";
import DocumentsPage from "./pages/DocumentsPage";
import EditorPage from "./pages/EditorPage";
import LoginPage from "./pages/LoginPage";
import { ToastContainer } from "react-toastify";
import RegisterPage from "./pages/RegisterPage";
import { AuthorizedContext } from "./utils/AuthorizedContext";
import { isAuthorized } from "./utils/common";

interface IAppState {
    isAuthorized: boolean;
}

interface MatchParams {
    documentId: string;
}

interface MatchProps extends RouteComponentProps<MatchParams> {
}

export default class App extends Component<{}, IAppState> {
    constructor({}) {
        super({});
        this.state = {
          isAuthorized: !!isAuthorized(),
        }
        
    }

    renderSwitch() {
        if (this.state.isAuthorized) {
          return (
            <Switch>
                <Route path="/secret" component={() => <SharedDocumentPage content={sampleMarkdown} />} />
                <Route path="/documents/:documentId" component={( {match}: MatchProps) => (
                    <EditorPage documentId={match.params.documentId} /> )}/>
                <Route path="/documents" component={() => <DocumentsPage getDocuments={getDocuments}/>} />
                <Redirect to="/documents" />
            </Switch>
          );
        }
      
        return (
          <Switch>
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
              <h1 className="page-header"><a href="/">MarkdownDocs</a></h1>
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