import React from "react";
import Markdown from 'react-markdown';

interface ISharedDocumentPageProps {
    content: string;
}

const SharedDocumentPage = ({content}: ISharedDocumentPageProps) => {
    return (
        <div style={{width: 860, margin: '0 auto'}}>
            <Markdown
                source={content}
            />
        </div>
    );
}

export default SharedDocumentPage;