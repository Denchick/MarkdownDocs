import MetaInfo from "../models/MetaInfo";
import { copyToClipboard } from "../utils/common";
import React, { Component } from "react";
import { Link } from "react-router-dom";
import { toShareDocument } from "../api/SharingApi";
import { toast } from "react-toastify";

interface IMetaInfoRowProps {
  metaInfo: MetaInfo;
  index: number;
  handleDelete: (documentId: string) => Promise<void>;
}

interface IMetaInfoRowState {
  shareToken: string | undefined;
}
export default class MetaInfoRow extends Component<IMetaInfoRowProps, IMetaInfoRowState> {
  constructor(props: IMetaInfoRowProps) {
    super(props);
    this.state = {shareToken: props.metaInfo.shareToken || undefined}
  }

  async handleShare() {
    const response = await toShareDocument(this.props.metaInfo.id);
    if (response.ok) {
      this.setState({shareToken: await response.text()});
    } else {
      toast.error("Something goes wrong");
    }
  }
  
  renderShare() {
    if (!this.state.shareToken) {
      return <button className="pure-button" onClick={this.handleShare.bind(this)}> share it!</button>
    }
    const path = `/share/${this.state.shareToken}`;
    return (
      <button className="pure-button" onClick={() => copyToClipboard(path)}>
        Copy <span role="img" aria-label="Copy">✂️</span> {path}
      </button>
    );
  }

  render() {
    const {metaInfo, index, handleDelete} = this.props;
    return (
      <tr className={index % 2 === 0 ? "pure-table-odd" : undefined} key={metaInfo.id}>
        <td>
          <Link to={`/documents/${metaInfo.id}/`}>
            {metaInfo.title || '<without name>'}
          </Link>
        </td>
        <td>{new Date(metaInfo.editedAt).toLocaleString()}</td>
        <td>{this.renderShare()}</td>
        <td>
          <button className="pure-button" onClick={async () => await handleDelete(metaInfo.id)}>
            Delete?
          </button>
        </td>
      </tr>
    );
  }
}