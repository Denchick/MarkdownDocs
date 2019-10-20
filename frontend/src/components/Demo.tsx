import React from 'react';
import Markdown from 'react-markdown';
import sampleMarkdown from '../sampleMarkdown';
import Editor from './Editor';

interface IDemoState {
  markdownSource: string;
}

class Demo extends React.PureComponent<{}, IDemoState> {
  state = {
    markdownSource: sampleMarkdown,
  }
  
  handleMarkdownChange(e: any) {
    this.setState({markdownSource: e.target.value})
  }

  render() { // мб ReactMarkdownWithHtml
    return (
      <div className="demo">
        <div className="editor-pane">
          <Editor value={this.state.markdownSource} onChange={this.handleMarkdownChange} />
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

export default Demo;