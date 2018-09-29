SRC_DIR="E:/github/java/efungame/src/efungame/efg_protobuf/src/main/resources/app"
# DEST_DIR="E:/github/java/efungame/src/efungame/efg_protobuf/target"
RM_DEST_DIR="E:/github/java/efungame/src/efungame/efg_protobuf/src/main/java/com/efun/game/protobuf/app/*"
DEST_DIR="E:/github/java/efungame/src/efungame/efg_protobuf/src/main/java"
PROTO_FILE="$SRC_DIR/*.proto"
EFG_PROTOBUF_ROOT="E:/github/java/efungame/src/efungame/efg_protobuf"

echo "cd $SRC_DIR" && cd $SRC_DIR

echo "rm -rf $RM_DEST_DIR" && rm -rf $RM_DEST_DIR
#echo "mkdir $DEST_DIR" && mkdir $DEST_DIR
echo "protoc.exe -I=$SRC_DIR --java_out=$DEST_DIR $PROTO_FILE" && protoc.exe -I=$SRC_DIR --java_out=$DEST_DIR $PROTO_FILE

PROTO_FILE="$SRC_DIR/*/*.proto"
echo "protoc.exe -I=$SRC_DIR --java_out=$DEST_DIR $PROTO_FILE" && protoc.exe -I=$SRC_DIR --java_out=$DEST_DIR $PROTO_FILE

echo "cd $EFG_PROTOBUF_ROOT" && cd $EFG_PROTOBUF_ROOT
# echo "mvn install -e -U" && mvn install -e -U
