import React, { Component } from "react";
import Markdown from 'react-markdown';
import { getSharedDocumentContent } from "../api/SharingApi";
import { toast } from "react-toastify";

interface ISharedDocumentPageProps {
    token: string;
}

interface ISharedDocumentPageState {
    content: string;
}

export default class SharedDocumentPage extends Component<ISharedDocumentPageProps, ISharedDocumentPageState> {
    constructor(props: ISharedDocumentPageProps) {
        super(props);
        this.state = {content: ''}
    }

    async componentDidMount() {
        const response = await getSharedDocumentContent(this.props.token);
        if (!response.ok) {
            toast.error('Something goes wrong');
        }
        this.setState({content: await response.text()});
    }

    render() {
        return (
            <div style={{width: 860, margin: '0 auto'}}>
                <Markdown
                    source={this.state.content}
                />
            </div>
        );
    }
}