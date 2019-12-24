import React from "react";
import MetaInfo from "../domain/MetaInfo";
import { Link } from "react-router-dom";

interface IDocumentsListProps {
  infos: MetaInfo[];
  handleDelete: (documentId: string) => void;
}

const DocumentsList = ({infos, handleDelete}: IDocumentsListProps) => {
  const renderHeader = () => {
    return (
      <tr key="header">
        <th>Title</th>
        <th>Last update at</th>
        <th>Author</th>
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
        <td>{metaInfo.author || '<unknown author>'}</td>
        <td><button className="pure-button" onClick={() => handleDelete(metaInfo.id)}>Delete?</button></td>
      </tr>
    );
  }
  
  return (
      <table className="pure-table">
        <thead>{renderHeader()}</thead>
        <tbody>{infos.map((info, index) => renderMetaInfo(info, index))}</tbody>
      </table>
  );
}

export default DocumentsList;