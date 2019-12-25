import React from 'react';
import { BrowserRouter, Switch, Route, Redirect, RouteComponentProps } from "react-router-dom";
import DocumentsPage from './pages/DocumentsPage';
import EditorPage from './pages/EditorPage';
import { getDocuments } from './api/DocumentsApi';
import LoginPage from './pages/LoginPage';
import UserInfoForm from './pages/RegisterPage';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

interface MatchParams {
  documentId: string;
}

interface MatchProps extends RouteComponentProps<MatchParams> {
}

const App = () => {
  return (
    <BrowserRouter>
      <div className="demo">
        <Switch>
          <Route path="/documents/:documentId" component={( {match}: MatchProps) => (
            <EditorPage documentId={match.params.documentId} /> )}/>
          <Route path="/documents" component={() => <DocumentsPage getDocuments={getDocuments}/>} />
          <Route path="/login" component={LoginPage} />
          <Route path="/register" component={UserInfoForm} />
          <Route exact path="/">
            <Redirect to="/documents" />
          </Route>
        </Switch>
        <ToastContainer closeOnClick hideProgressBar autoClose={3000} position="bottom-center"/>
      </div>
    </BrowserRouter>
  )
};

export default App;