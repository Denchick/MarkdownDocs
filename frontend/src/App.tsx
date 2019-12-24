import React from 'react';
import { BrowserRouter, Switch, Route, Redirect, RouteComponentProps } from "react-router-dom";
import DocumentsPage from './pages/DocumentsPage';
import EditorPage from './pages/EditorPage';
import { getDocuments } from './api/DocumentsApi';

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
          <Route exact path="/">
            <Redirect to="/documents" />
          </Route>
        </Switch>
      </div>
    </BrowserRouter>
  )
};

export default App;