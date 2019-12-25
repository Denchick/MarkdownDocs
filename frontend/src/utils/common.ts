import { toast } from "react-toastify";

export const copyToClipboard = (text: string) => {
    navigator.clipboard.writeText(`http://localhost:3000${text}`);
    toast.info("Copied!");
  }