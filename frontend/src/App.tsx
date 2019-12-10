import React from 'react';
import { BrowserRouter, Switch, Route, Redirect } from "react-router-dom";
import DocumentsPage from './pages/DocumentsPage';
import EditorPage from './pages/EditorPage';
import MockDocumentsApi from './api/MockDocumentsApi';

const App = () => {
  const api = new MockDocumentsApi();
  return (
    <BrowserRouter>
      <div className="demo">
        <Switch>
          <Route path="/documents/:documentId" component={EditorPage}/>
          <Route path="/documents" component={() => <DocumentsPage getDocuments={() => api.getDocuments()}/>} />
          <Route exact path="/">
            <Redirect to="/documents" />
          </Route>
        </Switch>
      </div>
    </BrowserRouter>
  )
};

export default App;