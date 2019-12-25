import React from 'react';
import { BrowserRouter, Switch, Route, Redirect, RouteComponentProps } from "react-router-dom";
import DocumentsPage from './pages/DocumentsPage';
import EditorPage from './pages/EditorPage';
import { getDocuments } from './api/DocumentsApi';
import LoginPage from './pages/LoginPage';
import UserInfoForm from './pages/RegisterPage';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Cookies from 'js-cookie';
import SharedDocumentPage from './pages/SharedDocumentPage';
import sampleMarkdown from './sampleMarkdown';


interface MatchParams {
  documentId: string;
}

interface MatchProps extends RouteComponentProps<MatchParams> {
}

const App = () => {
  return (
    <BrowserRouter>
      <div className="demo">
        {renderSwitch()}
        <ToastContainer closeOnClick hideProgressBar autoClose={3000} position="bottom-center"/>
      </div>
    </BrowserRouter>
  )
};

const renderSwitch = () => {
  const isAuthorized = Cookies.get('auth') && Cookies.get('userId');
  if (isAuthorized) {
    return (
      <Switch>
        <Route path="/secret" component={() => <SharedDocumentPage content={sampleMarkdown as unknown as string} />} />
        <Route path="/documents/:documentId" component={( {match}: MatchProps) => (
          <EditorPage documentId={match.params.documentId} /> )}/>
        <Route path="/documents" component={() => <DocumentsPage getDocuments={getDocuments}/>} />
        <Route exact path="/">
          <Redirect to="/documents" />
        </Route>
      </Switch>
    );
  }

  return (
    <Switch>
      <Route path="/login" component={LoginPage} />
      <Route path="/register" component={UserInfoForm} />
      <Redirect to="/login" />
    </Switch>
  );
}

export default App;