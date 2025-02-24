"use client";

export default function ClientLayout({
  children,
  fontVariable,
  fontClassName,
}: Readonly<{
  children: React.ReactNode;
  fontVariable: string;
  fontClassName: string;
}>) {
  return (
    <html lang="en" suppressHydrationWarning className={`${fontVariable}`}>
      <body className={` antialiased w-screen h-screen ${fontClassName}`}>
        {children}
      </body>
    </html>
  );
}
