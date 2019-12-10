import { PureComponent } from "react";
import Document from "../domain/Document";

interface IDocumentsPageProps {
  getDocuments: () => Promise<Document[]>;
}

interface IDocumentsPageState {
  documents: Document[];
}

export default class DocumentsPage extends PureComponent<IDocumentsPageProps, IDocumentsPageState>  {
  constructor(props: IDocumentsPageProps) {
    super(props);
    this.state = {documents: []}
  }
  componentDidMount() {
    console.log("call componentDidMount in DocumentPage");
    this.props.getDocuments()
      .then(data => this.setState({documents: data}) );
  }
  
  render() {
    console.log(this.state.documents);
    return `This is documents: ${this.state.documents}`;
  }
}