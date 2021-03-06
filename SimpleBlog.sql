USE [SimpleBlogDB]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 10/6/2021 7:34:45 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[email] [varchar](255) NOT NULL,
	[name] [nvarchar](50) NULL,
	[password] [varchar](64) NULL,
	[roleId] [int] NULL,
	[statusId] [int] NULL,
	[authenCode] [char](8) NULL,
 CONSTRAINT [PK_Account] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Article]    Script Date: 10/6/2021 7:34:45 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Article](
	[id] [char](8) NOT NULL,
	[title] [nvarchar](200) NULL,
	[description] [nvarchar](500) NULL,
	[content] [nvarchar](max) NULL,
	[postingDate] [datetime] NULL,
	[author] [varchar](255) NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_Articles_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ArticleComment]    Script Date: 10/6/2021 7:34:45 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ArticleComment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[articleId] [char](8) NULL,
	[commenter] [varchar](255) NULL,
	[postingDate] [datetime] NULL,
	[status] [int] NULL,
	[content] [nvarchar](max) NULL,
 CONSTRAINT [PK_ArticleComments_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[Article]  WITH CHECK ADD  CONSTRAINT [FK_Article_Account] FOREIGN KEY([author])
REFERENCES [dbo].[Account] ([email])
GO
ALTER TABLE [dbo].[Article] CHECK CONSTRAINT [FK_Article_Account]
GO
ALTER TABLE [dbo].[ArticleComment]  WITH CHECK ADD  CONSTRAINT [FK_ArticleComment_Account] FOREIGN KEY([commenter])
REFERENCES [dbo].[Account] ([email])
GO
ALTER TABLE [dbo].[ArticleComment] CHECK CONSTRAINT [FK_ArticleComment_Account]
GO
ALTER TABLE [dbo].[ArticleComment]  WITH CHECK ADD  CONSTRAINT [FK_ArticleComment_Article] FOREIGN KEY([articleId])
REFERENCES [dbo].[Article] ([id])
GO
ALTER TABLE [dbo].[ArticleComment] CHECK CONSTRAINT [FK_ArticleComment_Article]
GO
