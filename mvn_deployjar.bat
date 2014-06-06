@echo off
@rem Humane-Libのjarファイルを社内リポジトリに登録する。
@rem Humane-Libは別プロジェクトから参照されるため社内リポジトリを最新化しておく必要がある。
@rem mvnの社内リポジトリとしてNAS(\\LS-WSXL973)を参照しているので、接続できる状態で実行すること。


@rem 社内リポジトリに登録
@rem 必要に応じて編集・コメントアウトしてください。
echo ####### デプロイ処理 #######
call :deploy "ConfigLib"         "1.0" "target/ConfigLib.jar"
call :deploy "DBUtility"         "1.0" "target/DBUtility.jar"
call :deploy "ExceptionLib"      "1.0" "target/ExceptionLib.jar"
call :deploy "FileOperatorUtil"  "1.0" "target/FileOperatorUtil.jar"
call :deploy "HCharEncoder"      "1.0" "target/HCharEncoder.jar"
call :deploy "Logger"            "1.0" "target/Logger.jar"
call :deploy "MessageLib"        "1.0" "target/MessageLib.jar"
call :deploy "MorphemeEngineLib" "1.0" "target/MorphemeEngineLib.jar"
call :deploy "OpenCVLib"         "1.0" "target/OpenCVLib.jar"
call :deploy "StateMachineLib"   "1.0" "target/StateMachineLib.jar"
call :deploy "XMLLib"            "1.0" "target/XMLLib.jar"

echo ####### 正常に終了しました #######
pause
exit /b

# デプロイ処理
:deploy

@rem ビルドを行う
echo [%1のビルドを行います]
call mvn -f %1/pom.xml package

@rem 
echo [%1のjarファイルをデプロイします]
call mvn -f %1/pom.xml deploy:deploy-file -DgroupId="jp.co.humane-lib" -DartifactId=%1 -Dversion=%2 -Dpackaging=jar -Durl="file://${inside.repogitory.path}" -Dfile=%3 -DrepositoryId="inside.repogitory"

exit /b
