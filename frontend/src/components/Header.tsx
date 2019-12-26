import React from "react";
import Cookies from "js-cookie";
import { AuthorizedContext } from "../utils/AuthorizedContext";

const Header = () => {
    const handleLogout = (changeValue: (value: boolean) => void) => {
        Cookies.remove('userId');
        Cookies.remove('auth');
        changeValue(false);
    }

    const renderLogoutButton = () => {
        return (
            <AuthorizedContext.Consumer>
                {
                    ({isAuthorized, changeValue}) => {
                        if (!isAuthorized) {
                            return null;
                        }
                        return (
                            <button
                                type="button"
                                style={{margin: 10}}
                                className="button-xlarge pure-button"
                                onClick={() => handleLogout(changeValue)}
                            >
                                Logout
                            </button>
                        )
                    }
                }
            </AuthorizedContext.Consumer>
        );
    }

    return (
        <div className="page-header">
            <h1><a href="/">MarkdownDocs</a></h1>
            {renderLogoutButton()}
        </div>
    );
}

export default Header;