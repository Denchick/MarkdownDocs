import React from 'react';
import Markdown from 'react-markdown';
import sampleMarkdown from '../sampleMarkdown';
import Editor from '../components/Editor';

interface IEditorPageState {
  markdownSource: string;
}

interface IEditorPageProps {
  documentId: string;
}

export default class EditorPage extends React.PureComponent<IEditorPageProps, IEditorPageState> {
  constructor(props: IEditorPageProps) {
    super(props)

    this.handleMarkdownChange = this.handleMarkdownChange.bind(this)
    this.state = {
      markdownSource: sampleMarkdown
    }
    console.log(`Document id is ${props.documentId}`);
  }
  
  handleMarkdownChange(value: string) {
    this.setState({markdownSource: value})
  }

  render() {
    return (
      <div className="demo">
        <div className="editor-pane">
          <Editor
            value={this.state.markdownSource}
            onChange={this.handleMarkdownChange}
          />
        </div>

        <div className="result-pane">
          <Markdown 
            className="result"
            source={this.state.markdownSource}
          />
        </div>
      </div>
    )
  }
}