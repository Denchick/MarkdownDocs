import React from 'react';
import Markdown from 'react-markdown';
import { getDocument, updateDocument } from '../api/DocumentsApi';
import MetaInfo from '../models/MetaInfo';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Document from "../models/Document";
const CodeMirror = require('react-codemirror');

interface IEditorPageState {
  markdownSource: string | null;
  metaInfo: MetaInfo;
}

interface IEditorPageProps {
  documentId: string;
}

const codeMirrorOptions = {
  mode: 'markdown',
  theme: 'monokai',
  autofocus: true
};

export default class EditorPage extends React.PureComponent<IEditorPageProps, IEditorPageState> {
  cm: any;
  constructor(props: IEditorPageProps) {
    super(props)

    this.handleMarkdownChange = this.handleMarkdownChange.bind(this)
    this.state = {
      markdownSource: null,
      metaInfo: null as unknown as MetaInfo
    }
  }
  
  handleMarkdownChange(value: string) {
    this.setState({markdownSource: value})
  }
  async componentDidMount() {
    const response = await getDocument(this.props.documentId);
    if (!response.ok) {
      toast.error("Something goes wrong");
      return;
    }
    const document = response.json() as unknown as Document;
    this.setState({
      markdownSource: document.content,
      metaInfo: document.metaInfo
    });
    this.cm.codeMirror.setValue(document.content);
  }

  async handleSave(state: IEditorPageState) {
    const document = {
      content: state.markdownSource || '',
      metaInfo: state.metaInfo
    };
    toast.info("Document saved successfully!");
    await updateDocument(document);
  }

  render() {
    if (this.state.markdownSource === null) {
      return null;
    }

    return (
      <div className="demo">
        <div className="editor-pane">
          <form className="editor pure-form">
            <CodeMirror
              ref={(c: any) => this.cm = c}
              options={codeMirrorOptions}
              value={this.state.markdownSource}
              onChange={this.handleMarkdownChange}
            />
          </form>
        </div>
        <div className="result-pane">
          <Markdown 
            className="result"
            source={this.state.markdownSource}
          />
        </div>
        <button className="saveButton pure-button" onClick={async () => await this.handleSave(this.state)}>
          Save changes
        </button>
      </div>
    )
  }
}