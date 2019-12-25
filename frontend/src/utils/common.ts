import { toast } from "react-toastify";
import Cookies from "js-cookie";

export const copyToClipboard = (text: string) => {
  navigator.clipboard.writeText(`http://localhost:3000${text}`);
  toast.info("Copied!");
}

export const isAuthorized = () => Cookies.get('auth') && Cookies.get('userId');