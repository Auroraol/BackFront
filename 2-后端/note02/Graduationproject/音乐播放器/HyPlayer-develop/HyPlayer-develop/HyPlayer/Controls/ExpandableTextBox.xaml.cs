﻿using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

//https://go.microsoft.com/fwlink/?LinkId=234236 上介绍了“用户控件”项模板

namespace HyPlayer.Controls;

public sealed partial class ExpandableTextBox : UserControl
{
    public static readonly DependencyProperty ButtonTextProperty = DependencyProperty.Register(
        "ButtonText", typeof(string), typeof(ExpandableTextBox), new PropertyMetadata("展开"));

    public static readonly DependencyProperty SelectableProperty = DependencyProperty.Register(
        "Selectable", typeof(bool), typeof(SelectableTextBox), new PropertyMetadata(true));

    public static readonly DependencyProperty TextProperty = DependencyProperty.Register(
        "Text", typeof(string), typeof(SelectableTextBox), new PropertyMetadata(default(string)));

    public static readonly DependencyProperty MaxLinesProperty = DependencyProperty.Register(
        "MaxLines", typeof(int), typeof(SelectableTextBox), new PropertyMetadata(3));

    public static readonly DependencyProperty TextWrappingProperty = DependencyProperty.Register(
        "TextWrapping", typeof(TextWrapping), typeof(SelectableTextBox),
        new PropertyMetadata(default(TextWrapping)));

    private static readonly DependencyProperty ActualMaxLineProperty = DependencyProperty.Register(
        "ActualMaxLine", typeof(int), typeof(ExpandableTextBox), new PropertyMetadata(7));

    private bool _isExpanded;

    public ExpandableTextBox()
    {
        InitializeComponent();
        MyTextBlock.IsTextTrimmedChanged += MyTextBlock_IsTextTrimmedChanged;
        ExpandButton.Visibility = Visibility.Collapsed;
    }

    private void MyTextBlock_IsTextTrimmedChanged(TextBlock sender, IsTextTrimmedChangedEventArgs args)
    {
        if (MyTextBlock.IsTextTrimmed || _isExpanded)
        {
            ExpandButton.Visibility = Visibility.Visible;
        }
        else
        {
            ExpandButton.Visibility = Visibility.Collapsed;
        }
    }

    public string ButtonText
    {
        get => (string)GetValue(ButtonTextProperty);
        set => SetValue(ButtonTextProperty, value);
    }

    public bool Selectable
    {
        get => (bool)GetValue(SelectableProperty);
        set => SetValue(SelectableProperty, value);
    }

    public string Text
    {
        get => (string)GetValue(TextProperty);
        set
        {
            SetValue(TextProperty, value);
        }
    }


    public int MaxLines
    {
        get => (int)GetValue(MaxLinesProperty);
        set => SetValue(MaxLinesProperty, value);
    }

    public TextWrapping TextWrapping
    {
        get => (TextWrapping)GetValue(TextWrappingProperty);
        set => SetValue(TextWrappingProperty, value);
    }

    private int ActualMaxLine
    {
        get => (int)GetValue(ActualMaxLineProperty);
        set => SetValue(ActualMaxLineProperty, value);
    }

    private void ExpandOrCollapseText(object sender, RoutedEventArgs e)
    {
        if (_isExpanded)
        {
            ButtonText = "展开";
            ActualMaxLine = MaxLines;
        }
        else
        {
            ButtonText = "收起";
            ActualMaxLine = int.MaxValue;
        }

        _isExpanded = !_isExpanded;
    }
}