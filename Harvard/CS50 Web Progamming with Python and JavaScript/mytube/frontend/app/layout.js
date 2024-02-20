import { Inter } from "next/font/google";
import "./globals.css";
import { AppRouterCacheProvider } from "@mui/material-nextjs/v13-appRouter";
import CssBaseline from "@mui/material/CssBaseline";
const inter = Inter({ subsets: ["latin"] });

export default function RootLayout({ children }) {
    return (
        <html lang="en">
            <body className={`${inter.className}`}>
                <AppRouterCacheProvider options={{ enableCssLayer: true }}>
                    <CssBaseline />
                    {children}
                </AppRouterCacheProvider>
            </body>
        </html>
    );
}
