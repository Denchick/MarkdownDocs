import React from "react";
import MetaInfo from "../models/MetaInfo";
import MetaInfoRow from "./MetaInfoRow";

interface IDocumentsListProps {
  infos: MetaInfo[];
  handleDelete: (documentId: string) => Promise<void>;
}

const DocumentsList = ({infos, handleDelete}: IDocumentsListProps) => {
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
  
  return (
      <table className="pure-table">
        <thead>{renderHeader()}</thead>
        <tbody>{infos.map((info, index) => <MetaInfoRow metaInfo={info} index={index} handleDelete={handleDelete}/>)}</tbody>
      </table>
  );
}

export default DocumentsList;