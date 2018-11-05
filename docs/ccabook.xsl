<?xml version="1.0" encoding="UTF-8" standalone="no"?><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
            <head>
                <title>CCA</title>
                <h3>CCA Budget book</h3>
            </head>
            <body>
                <xsl:for-each select="ccabook/ccas">
                    <xsl:if test="name='Hockey'">
                        <p>
                            CCA:
                            <xsl:value-of select="name"/>
                            <br/>
                            Head:
                            <xsl:value-of select="head"/>
                            <br/>
                            Vice-Head:
                            <xsl:value-of select="viceHead"/>
                            <br/>
                        </p>
                        <p>
                            Budget: $
                            <xsl:value-of select="budget"/>
                            <br/>
                            Outstanding: $
                            <xsl:value-of select="outstanding"/>
                            <br/>
                            Spent: $
                            <xsl:value-of select="spent"/>
                            <br/>
                            <br/>
                            <h3>Transaction: </h3>
                        </p>
                        <table border="1">
                            <tr>
                                <th>S/N</th>
                                <th>Date</th>
                                <th>Amount ($)</th>
                                <th>Remarks</th>
                            </tr>

                            <xsl:for-each select="transaction">
                                <tr>
                                    <td><xsl:value-of select="entryNum"/></td>
                                    <td><xsl:value-of select="date"/></td>
                                    <td align="right"><xsl:value-of select="amount"/></td>
                                    <td><xsl:value-of select="log"/></td>
                                </tr>
                            </xsl:for-each>
                        </table>
                    </xsl:if>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
