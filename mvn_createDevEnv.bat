@echo off

@rem �J�����\�z�p�o�b�`�t�@�C��
@rem git�Ńt�@�C�����_�E�����[�h��ɖ{�o�b�`���N�����邱�ƂŊJ���ł�����ɂ���B
@rem mvn�̎Г����|�W�g���Ƃ���NAS(\\LS-WSXL973)���Q�Ƃ��Ă���̂ŁA�ڑ��ł����ԂŎ��s���邱�ƁB

@rem target�f�B���N�g�����폜
echo ####### clean���� #######
call mvn clean

@rem ExceptionLib�͑��v���W�F�N�g����Q�Ƃ����̂Ńr���h���Ă���
echo ####### ���O�r���h���� #######
call mvn -f ExceptionLib\pom.xml package
if not "%ERRORLEVEL%" == "0" exit /b

@rem �ˑ�����jar�t�@�C�����_�E�����[�h
echo ####### jar�t�@�C���_�E�����[�h���� #######
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

@rem eclipse����r���h�\�ɂ��邽�߂̏���
@rem �e�X�g�N���X����U���Εs�v
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


echo ####### ����I�����܂��� #######
pause
exit /b
