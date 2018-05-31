# gcf2018-geoevent-twitter
GISコミュニティフォーラム2018年GeoEventデモ用

## 概要
SNSにより人、モノ、商品などに対する好感度をリアルタイムに測定して地図上に可視化します。
これにより特定の商品やサービスなどのイメージをビジュアルに地図上で把握することが可能となります。
2018年5月末に開催されましたGISコミュニティフォーラムにてデモ展示しました内容を一部公開します。

### Twitter 上の位置情報タグなしのツィートを地図上に表示するには？
ところでTwitter上で位置情報タグ付きのツィートは日本では全体数パーセント程度で非常に少ないのが現実です。
そこで位置情報がないツィートも文章中に地域に関する名称などが含まれていた場合に簡易ジオコード機能にて座標に落とす試みをしてみました。
本レポジトリで公開している簡易ジオコードプロセッサーでは大字レベルまでのジオコードを行っています。

### テキスト解析
テキスト解析では日本語の文章を形態素解析により分割して地域に関する名称、文章中のポジティブやネガティブ度合いをスコアに落としています。
ツィート文をこのテキスト解析プロセッサーに通せば、地域名称、好感度スコアなどを取得できます。

※ポジティブやネガティブ判定は研究用の辞書を利用しておりますがGithub上では公開しておりません。一応、辞書なしでも動作できるようにしておりますがスコアはすべて100と判定されます。

### コーヒーに関するSNS好感度リアルタイムエリア判定イメージ
上記のテキスト解析、位置情報なしのツィートを地図上へ落とす簡易ジオコードプロセッサーなどを利用して、リアルタイムに特定の人、モノ、商品などに対する好感度をエリア別に表示することが可能となります。ここではコーヒーに関するツィートの好感度判定の平均値をエリア別に表示している例になります。青いエリアほど好感度が高い、という判定です。
![コーヒーに関するSNS好感度リアルタイムエリア判定イメージ](https://github.com/EsriJapan/gcf2018-geoevent-twitter/blob/master/images/sns_area_coffee_tokyo.JPG)

### 必要な資材などは？
Portal for ArcGIS, ArcGIS GeoEvent Processor, ArcGIS DataStore のセットアップが必要となります。
セットアップ方法は以下のサイトをご覧ください。
* http://enterprise.arcgis.com/ja/portal/latest/install/windows/welcome-to-the-portal-for-arcgis-installation-guide.htm
* http://enterprise.arcgis.com/ja/geoevent/latest/install/windows/installing-geoevent.htm
* http://enterprise.arcgis.com/ja/portal/latest/administer/windows/install-data-store.htm

### ArcGIS GeoEvent Server 管理画面イメージ
ArcGIS GeoEvent Server にてリアルタイム好感度解析サービスを公開します。
INPUTコネクターにTwitterのコネクタを選択し、OUTPUTコネクターにソケット出力、ストリームレイヤー、ビッグデータストアの３つの出力を選択します。
管理画面イメージの黄色い処理がプロセッサーと呼ばれるもので本サイトで公開しているテキスト解析プロセッサーと簡易ジオコードプロセッサーも含んでいます。
Twitter コネクターに関しましては以下のサイトにて公開されております。
* https://github.com/Esri/twitter-for-geoevent
![ArcGIS GeoEvent Server 管理画面イメージ](https://github.com/EsriJapan/gcf2018-geoevent-twitter/blob/master/images/geoevent_manager_sample.PNG)

## lightgeocode-processor（簡易ジオコードプロセッサ）
簡易ジオコードプロセッサは、地域に関する名称より緯度経度の座標を ArcGIS GeoEvent Server 内のジオイベント上にセットします。

## textanalysis-processor（テキスト解析プロセッサ）
テキスト解析プロセッサは、ツィート本文内のテキストを形態素解析し、好感度の辞書が存在する場合は文章中の好感度をスコア化してジオイベントにセットします。
スコアの標準値は100を基準としています。

### 辞書の配置場所
「:」コロン区切りです。必要に応じてコード修正して読み込みできます。
* 日本語の場合 ⇒ src/main/resources/dic/pn_ja_utf8.dic

| 語句 | 読み方 | 品詞 | スコア |
----|----|----|----
| 好き | すき | 動詞 | 0.987 |

* 英語の場合 ⇒ src/main/resources/dic/pn_en.dic

| 語句 | 品詞 | スコア |
----|----|----
| like | v | 0.987 |

## ビルド環境
Eclipse 4.7 Oxygen を利用してビルド確認しております。
Maven プロジェクトになってます。
pom.xml を選択して、mvnのゴールに「clean package」と指定すればビルド可能です。
* http://mergedoc.osdn.jp/

## License
MIT

## 注意事項
* GeoEvent Server SDK は2018年5月時点で日本国内未サポートとなります。
* ジオコード処理、好感度スコア判定など精度上改善すべき課題があります。
