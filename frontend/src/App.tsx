import React from 'react';
import { BrowserRouter, Switch, Route, Link, Redirect } from "react-router-dom";
import DocumentsPage from './pages/DocumentsPage';
import EditorPage from './pages/EditorPage';
import MockDocumentsApi from './api/MockDocumentsApi';

const App = () => {
  const api = new MockDocumentsApi();
  return (
    <BrowserRouter>
      <div className="demo">
        <Link to="/documents">documents list</Link> <br />
        <Link to="/documents/thisisid">Document</Link>
      </div>
      <Switch>
        <Route path="/documents/:documentId" component={EditorPage}/>
        <Route path="/documents" component={() => <DocumentsPage getDocuments={() => api.getDocuments()}/>} />
        <Route exact path="/">
          <Redirect to="/documents" />
        </Route>
      </Switch>
    </BrowserRouter>
  )
};

export default App;