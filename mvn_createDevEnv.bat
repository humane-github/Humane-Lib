@echo off

@rem 開発環境構築用バッチファイル
@rem gitでファイルをダウンロード後に本バッチを起動することで開発できる環境にする。
@rem mvnの社内リポジトリとしてNAS(\\LS-WSXL973)を参照しているので、接続できる状態で実行すること。

@rem targetディレクトリを削除
echo ####### clean処理 #######
call mvn clean

@rem ExceptionLibは他プロジェクトから参照されるのでビルドしておく
echo ####### 事前ビルド処理 #######
call mvn -f ExceptionLib\pom.xml package
if not "%ERRORLEVEL%" == "0" exit /b

@rem 依存するjarファイルをダウンロード
echo ####### jarファイルダウンロード処理 #######
call mvn -f ConfigLib\pom.xml         dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b
call mvn -f DBUtility\pom.xml         dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b
call mvn -f ExceptionLib\pom.xml      dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b
call mvn -f FileOperatorUtil\pom.xml  dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b
call mvn -f HCharEncoder\pom.xml      dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b
call mvn -f Logger\pom.xml            dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b
call mvn -f MessageLib\pom.xml        dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b
call mvn -f MorphemeEngineLib\pom.xml dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b
call mvn -f OpenCVLib\pom.xml         dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b
call mvn -f StateMachineLib\pom.xml   dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b
call mvn -f XMLLib\pom.xml            dependency:copy-dependencies -DoutputDirectory=src/main/lib
if not "%ERRORLEVEL%" == "0" exit /b

@rem eclipseからビルド可能にするための処理
@rem テストクラスを一旦作れば不要
if not exist ConfigLib\src\test\java mkdir ConfigLib\src\test\java
if not exist DBUtility\src\test\java mkdir DBUtility\src\test\java
if not exist ExceptionLib\src\test\java mkdir ExceptionLib\src\test\java
if not exist FileOperatorUtil\src\test\java mkdir FileOperatorUtil\src\test\java
if not exist HCharEncoder\src\test\java mkdir HCharEncoder\src\test\java
if not exist Logger\src\test\java mkdir Logger\src\test\java
if not exist MessageLib\src\test\java mkdir MessageLib\src\test\java
if not exist MorphemeEngineLib\src\test\java mkdir MorphemeEngineLib\src\test\java
if not exist OpenCVLib\src\test\java mkdir OpenCVLib\src\test\java
if not exist StateMachineLib\src\test\java mkdir StateMachineLib\src\test\java
if not exist XMLLib\src\test\java mkdir XMLLib\src\test\java


echo ####### 正常終了しました #######
pause
exit /b
