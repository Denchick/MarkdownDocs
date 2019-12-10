import { PureComponent } from "react";
import { Table } from "semantic-ui-react";
import React from "react";
import MetaInfo from "../domain/MetaInfo";
import { Link } from "react-router-dom";

interface IDocumentsPageProps {
  getDocuments: () => Promise<MetaInfo[]>;
}

interface IDocumentsPageState {
  infos: MetaInfo[];
}

export default class DocumentsPage extends PureComponent<IDocumentsPageProps, IDocumentsPageState>  {
  constructor(props: IDocumentsPageProps) {
    super(props);
    this.state = {infos: []}
  }
  componentDidMount() {
    console.log("call componentDidMount in DocumentPage");
    this.props.getDocuments()
      .then(data => this.setState({infos: data}) );
  }

  renderHeader() {
    return (
      <Table.Header>
        <Table.Row>
          <Table.HeaderCell>Title</Table.HeaderCell>
          <Table.HeaderCell>UpdatedAt</Table.HeaderCell>
          <Table.HeaderCell>Author</Table.HeaderCell>
        </Table.Row>
      </Table.Header>
    );
  }
  
  renderMetaInfo(metaInfo: MetaInfo) {
    return (
      <Table.Row>
        <Table.Cell>
          <Link to={`/documents/${metaInfo.documentId}`}>
            {metaInfo.title}
          </Link>
        </Table.Cell>
        <Table.Cell>{metaInfo.updatedAt.toJSON()}</Table.Cell>
        <Table.Cell>{metaInfo.author}</Table.Cell>
      </Table.Row>
    );
  }

  render() {
    return (
      <Table>
        {this.renderHeader()}
        {this.state.infos.map(info => this.renderMetaInfo(info))}
      </Table>
    );
  }
}