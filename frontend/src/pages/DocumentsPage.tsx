import { PureComponent } from "react";
import React from "react";
import MetaInfo from "../models/MetaInfo";
import DocumentsList from "../components/DocumentsList";
import { Redirect } from "react-router";
import { createDocument, deleteDocument } from "../api/DocumentsApi";
import Cookies from "js-cookie";

interface IDocumentsPageProps {
  getDocuments: () => Promise<MetaInfo[]>;
}

interface IDocumentsPageState {
  infos: MetaInfo[];
  newDocumentId: string;
  isLogouted: boolean;
}

export default class DocumentsPage extends PureComponent<IDocumentsPageProps, IDocumentsPageState>  {
  constructor(props: IDocumentsPageProps) {
    super(props);
    this.state = {
      infos: [],
      newDocumentId: '',
      isLogouted: false
    }
  }
  componentDidMount() {
    console.log("call componentDidMount in DocumentPage");
    this.props.getDocuments()
      .then(data => this.setState({infos: data}) );
  }

  async handleCreateDocument() {
    const documentId = await createDocument();
    this.setState({newDocumentId: documentId});
  }

  handleDelete = (documentId: string) => {
    this.setState({infos: this.state.infos.filter(info => info.id !== documentId)})
    deleteDocument(documentId);
  }

  handleLogout = () => {
    Cookies.remove('auth');
    Cookies.remove('userId');
    this.setState({isLogouted: true});
  }

  renderNoDocumentsMessage() {
    return (
      <p>
        You dont have documents yet &nbsp;&nbsp;&nbsp;
        <button
          className="pure-button"
          onClick={this.handleCreateDocument.bind(this)}
        >
          Create!
        </button>
      </p>
    );
  }

  render() {
    if (this.state.isLogouted) {
      return <Redirect to={`/login`} />;
    }
    if (this.state.newDocumentId)
      return <Redirect to={`/documents/${this.state.newDocumentId}`} />;
    if (this.state.infos.length === 0)
      return this.renderNoDocumentsMessage();
    return (
      <div>
        <DocumentsList infos={this.state.infos} handleDelete={this.handleDelete}/>
        <button type="button" className="button-xlarge pure-button" style={{margin: '10px 10px 0 0'}} onClick={this.handleCreateDocument.bind(this)}>
          Create new document!
        </button>
        <button type="button" className="button-xlarge pure-button" style={{marginTop: 10}} onClick={this.handleLogout}>
          Logout
        </button>
      </div>
    );
  }
}