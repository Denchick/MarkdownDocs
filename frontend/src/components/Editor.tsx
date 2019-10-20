import React from 'react';
import CodeMirror from 'react-codemirror';

interface IEditorProps {
    value: string;
    onChange: (event: any) => void;
}

const Editor = ({value, onChange}: IEditorProps) => {
  const codeMirrorOptions = {
    mode: 'markdown',
    theme: 'monokai',
    autofocus: true
  };

  return (
    <form className="editor pure-form">
      <CodeMirror
        options={codeMirrorOptions}
        value={value}
        onChange={onChange}
      />
    </form>
  )
}

export default Editor;