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

echo ####### ����I�����܂��� #######
pause
exit /b
