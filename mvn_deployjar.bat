@echo off
@rem Humane-Lib��jar�t�@�C�����Г����|�W�g���ɓo�^����B
@rem Humane-Lib�͕ʃv���W�F�N�g����Q�Ƃ���邽�ߎГ����|�W�g�����ŐV�����Ă����K�v������B
@rem mvn�̎Г����|�W�g���Ƃ���NAS(\\LS-WSXL973)���Q�Ƃ��Ă���̂ŁA�ڑ��ł����ԂŎ��s���邱�ƁB


@rem �Г����|�W�g���ɓo�^
@rem �K�v�ɉ����ĕҏW�E�R�����g�A�E�g���Ă��������B
echo ####### �f�v���C���� #######
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

echo ####### ����ɏI�����܂��� #######
pause
exit /b

# �f�v���C����
:deploy

@rem �r���h���s��
echo [%1�̃r���h���s���܂�]
call mvn -f %1/pom.xml package

@rem 
echo [%1��jar�t�@�C�����f�v���C���܂�]
call mvn -f %1/pom.xml deploy:deploy-file -DgroupId="jp.co.humane-lib" -DartifactId=%1 -Dversion=%2 -Dpackaging=jar -Durl="file://${inside.repogitory.path}" -Dfile=%3 -DrepositoryId="inside.repogitory"

exit /b
