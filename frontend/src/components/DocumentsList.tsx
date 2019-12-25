import React from "react";
import MetaInfo from "../models/MetaInfo";
import { Link } from "react-router-dom";
import { copyToClipboard } from "../utils/common";

interface IDocumentsListProps {
  infos: MetaInfo[];
  handleDelete: (documentId: string) => Promise<void>;
}

const DocumentsList = ({infos, handleDelete}: IDocumentsListProps) => {
  // const handleShareDocument = async () => {
  //  
  //}
  
  const renderHeader = () => {
    return (
      <tr key="header">
        <th>Title</th>
        <th>Last update at</th>
        <th>Sharing</th>
        <th></th>
      </tr>
    );
  }
    
  const renderMetaInfo = (metaInfo: MetaInfo, index: number) => {
    return (
      <tr className={index % 2 === 0 ? "pure-table-odd" : undefined} key={metaInfo.id}>
        <td>
          <Link to={`/documents/${metaInfo.id}/`}>
            {metaInfo.title || '<without name>'}
          </Link>
        </td>
        <td>{new Date(metaInfo.editedAt).toLocaleString()}</td>
        <td>{renderShare(metaInfo)}</td>
        <td><button className="pure-button" onClick={async () => await handleDelete(metaInfo.id)}>Delete?</button></td>
      </tr>
    );
  }

  const renderShare = (metaInfo: MetaInfo) => {
    // if (!metaInfo.shareToken) {
    //   return <a href="#" onClick={() => }
    // }
    const path = `/documents/${metaInfo.shareToken}`;
    return <a style={{textDecoration: 'underline'}} onClick={() => copyToClipboard(path)}>Copy ✂️ {path}</a>;
  }
  
  return (
      <table className="pure-table">
        <thead>{renderHeader()}</thead>
        <tbody>{infos.map((info, index) => renderMetaInfo(info, index))}</tbody>
      </table>
  );
}

export default DocumentsList;