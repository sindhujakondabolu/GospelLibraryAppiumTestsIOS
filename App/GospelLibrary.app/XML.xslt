<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html"/>
    
<xsl:template match="processing-instruction('xml-stylesheet')"></xsl:template>

<xsl:template match="/content">
  <html>
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0"/>
    <link href="XML.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
      function expand(id) {
        document.getElementById(id).className = 'expanded';
      }
      function collapse(id) {
        document.getElementById(id).className = 'collapsed';
      }
    </script>
  </head>
  <body>
    <xsl:apply-templates/>
  </body>
  </html>
</xsl:template>

<xsl:template match="node()">
  <span class="element">
    <xsl:choose>
      <xsl:when test="node()|text()">
        <span class="expanded">
          <xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
          <span class="open">
            <a class="toggle">
              <xsl:attribute name="onclick">javascript:collapse('<xsl:value-of select="generate-id(.)"/>')</xsl:attribute>
              ▾
            </a>
            <span class="tag">
              <span class="punctuation">&lt;</span>
              <span class="elementName"><xsl:value-of select="local-name()"/></span>
              <xsl:apply-templates select="@*"/>
              <span class="punctuation">&gt;</span>
            </span>
            <xsl:apply-templates/>
            <span class="tag">
              <span class="punctuation">&lt;/</span>
              <span class="elementName"><xsl:value-of select="local-name()"/></span>
              <span class="punctuation">&gt;</span>
            </span>
          </span>
          <span class="closed">
            <a class="toggle">
              <xsl:attribute name="onclick">javascript:expand('<xsl:value-of select="generate-id(.)"/>')</xsl:attribute>
              ▸
            </a>
            <span class="tag">
              <span class="punctuation">&lt;</span>
              <span class="elementName"><xsl:value-of select="local-name()"/></span>
              <xsl:apply-templates select="@*"/>
              <span class="punctuation">&gt;</span>
            </span>
            ...
            <span class="tag">
              <span class="punctuation">&lt;/</span>
              <span class="elementName"><xsl:value-of select="local-name()"/></span>
              <span class="punctuation">&gt;</span>
            </span>
          </span>
        </span>
      </xsl:when>
      <xsl:otherwise>
        <span class="tag">
          <span class="punctuation">&lt;</span>
          <span class="elementName"><xsl:value-of select="local-name()"/></span>
          <xsl:apply-templates select="@*"/>
          <span class="punctuation">/&gt;</span>
        </span>
      </xsl:otherwise>
    </xsl:choose>
  </span>
</xsl:template>
    
<xsl:template match="@*">
  <xsl:text> </xsl:text>
  <span class="attributeName"><xsl:value-of select="local-name()"/></span>
  <span class="punctuation">=</span>
  <span class="attributeValue"><xsl:value-of select="."/></span>
</xsl:template>
    
<xsl:template match="text()">
    <span class="text"><xsl:value-of select="."/></span>
</xsl:template>

<xsl:template match="comment()"></xsl:template>

</xsl:stylesheet>