import React from 'react';
import CodeMirror from 'react-codemirror';

interface IEditorProps {
    value: string;
    onChange: (event: any) => void;
}

const Editor = ({value, onChange}: IEditorProps) => {
  return (
    <form className="editor pure-form">
      <CodeMirror options={{
        mode: 'markdown',
        theme: 'monokai'
      }} value={value} onChange={onChange} />
    </form>
  )
}

export default Editor;