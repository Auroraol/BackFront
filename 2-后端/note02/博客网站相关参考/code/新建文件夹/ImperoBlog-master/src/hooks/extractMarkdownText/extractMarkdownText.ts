export const extractMarkdownText = (markdownText: string) => {
    // 移除Markdown的链接
    markdownText = markdownText.replace(/\[(.*?)\]\(.*?\)/g, '$1');

    // 移除Markdown的图片
    markdownText = markdownText.replace(/!\[.*\]\(.*\)/g, '');

    // 移除行内代码块
    markdownText = markdownText.replace(/`.*?`/g, '');

    // 移除其他Markdown标记
    markdownText = markdownText.replace(/[\*\_]{1,3}|~~|`|#{1,6}/g, '');

    // 移除多余的空格和换行
    markdownText = markdownText.replace(/\s+/g, ' ').trim();

    //移除叹号
    markdownText = markdownText.replace(/!/g, '');

    return markdownText;
}