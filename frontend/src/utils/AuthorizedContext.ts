import React from "react";

export const AuthorizedContext = React.createContext({
    isAuthorized: false,
    changeValue: (value: boolean) => {}
});