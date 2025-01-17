﻿using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Media;

namespace HyPlayer.Classes
{
    public class BrushManagement : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
        private Windows.UI.Color? karaokAccentBrush;
        private SolidColorBrush accentBrush;
        private SolidColorBrush idleBrush;
        public Windows.UI.Color KaraokAccentBrush
        {
            get
            {
                if (Common.Setting.karaokLyricFocusingColor is not null)
                {
                    return (Windows.UI.Color)Common.Setting.karaokLyricFocusingColor;
                }
                return karaokAccentBrush != null
                ? (Windows.UI.Color)karaokAccentBrush
                : (Application.Current.Resources["SystemControlPageTextBaseHighBrush"] as SolidColorBrush)!.Color;
            }
            set
            {
                karaokAccentBrush = value;
                NotifyPropertyChanged();
            }
        }

        public SolidColorBrush AccentBrush
        {
            get

            {
                if (Common.Setting.pureLyricFocusingColor is not null)
                {
                    return new SolidColorBrush(Common.Setting.pureLyricFocusingColor.Value);
                }

                return (accentBrush != null
                    ? accentBrush
                    : Application.Current.Resources["SystemControlPageTextBaseHighBrush"] as SolidColorBrush)!;
            }
            set
            {
                accentBrush = value;
                NotifyPropertyChanged();
            }
        }
        public SolidColorBrush IdleBrush
        {
            get
            {
                if (Common.Setting.pureLyricIdleColor is not null)
                {
                    return new SolidColorBrush(Common.Setting.pureLyricIdleColor.Value);
                }

                return (idleBrush != null
                    ? idleBrush
                    : Application.Current.Resources["TextFillColorTertiaryBrush"] as SolidColorBrush)!;
            }
            set
            {
                idleBrush = value;
                NotifyPropertyChanged();
            }
        }

    }
}
