// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message/UpdateUserSignInStatusResp.proto

package com.efun.game.protobuf.app.message;

public final class UpdateUserSignInStatusRespProto {
  private UpdateUserSignInStatusRespProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface UpdateUserSignInStatusRespOrBuilder extends
      // @@protoc_insertion_point(interface_extends:protobuf.app.message.UpdateUserSignInStatusResp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * 处理结果
     * </pre>
     *
     * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
     */
    boolean hasBaseResp();
    /**
     * <pre>
     * 处理结果
     * </pre>
     *
     * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
     */
    com.efun.game.protobuf.app.BaseRespProto.BaseResp getBaseResp();
    /**
     * <pre>
     * 处理结果
     * </pre>
     *
     * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
     */
    com.efun.game.protobuf.app.BaseRespProto.BaseRespOrBuilder getBaseRespOrBuilder();

    /**
     * <code>optional fixed64 sessionId = 2;</code>
     */
    boolean hasSessionId();
    /**
     * <code>optional fixed64 sessionId = 2;</code>
     */
    long getSessionId();
  }
  /**
   * Protobuf type {@code protobuf.app.message.UpdateUserSignInStatusResp}
   */
  public  static final class UpdateUserSignInStatusResp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:protobuf.app.message.UpdateUserSignInStatusResp)
      UpdateUserSignInStatusRespOrBuilder {
    // Use UpdateUserSignInStatusResp.newBuilder() to construct.
    private UpdateUserSignInStatusResp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private UpdateUserSignInStatusResp() {
      sessionId_ = 0L;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private UpdateUserSignInStatusResp(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              com.efun.game.protobuf.app.BaseRespProto.BaseResp.Builder subBuilder = null;
              if (((bitField0_ & 0x00000001) == 0x00000001)) {
                subBuilder = baseResp_.toBuilder();
              }
              baseResp_ = input.readMessage(com.efun.game.protobuf.app.BaseRespProto.BaseResp.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(baseResp_);
                baseResp_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000001;
              break;
            }
            case 17: {
              bitField0_ |= 0x00000002;
              sessionId_ = input.readFixed64();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.internal_static_protobuf_app_message_UpdateUserSignInStatusResp_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.internal_static_protobuf_app_message_UpdateUserSignInStatusResp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp.class, com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp.Builder.class);
    }

    private int bitField0_;
    public static final int BASERESP_FIELD_NUMBER = 1;
    private com.efun.game.protobuf.app.BaseRespProto.BaseResp baseResp_;
    /**
     * <pre>
     * 处理结果
     * </pre>
     *
     * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
     */
    public boolean hasBaseResp() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <pre>
     * 处理结果
     * </pre>
     *
     * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
     */
    public com.efun.game.protobuf.app.BaseRespProto.BaseResp getBaseResp() {
      return baseResp_ == null ? com.efun.game.protobuf.app.BaseRespProto.BaseResp.getDefaultInstance() : baseResp_;
    }
    /**
     * <pre>
     * 处理结果
     * </pre>
     *
     * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
     */
    public com.efun.game.protobuf.app.BaseRespProto.BaseRespOrBuilder getBaseRespOrBuilder() {
      return baseResp_ == null ? com.efun.game.protobuf.app.BaseRespProto.BaseResp.getDefaultInstance() : baseResp_;
    }

    public static final int SESSIONID_FIELD_NUMBER = 2;
    private long sessionId_;
    /**
     * <code>optional fixed64 sessionId = 2;</code>
     */
    public boolean hasSessionId() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional fixed64 sessionId = 2;</code>
     */
    public long getSessionId() {
      return sessionId_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasBaseResp()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!getBaseResp().isInitialized()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeMessage(1, getBaseResp());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeFixed64(2, sessionId_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getBaseResp());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFixed64Size(2, sessionId_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp)) {
        return super.equals(obj);
      }
      com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp other = (com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp) obj;

      boolean result = true;
      result = result && (hasBaseResp() == other.hasBaseResp());
      if (hasBaseResp()) {
        result = result && getBaseResp()
            .equals(other.getBaseResp());
      }
      result = result && (hasSessionId() == other.hasSessionId());
      if (hasSessionId()) {
        result = result && (getSessionId()
            == other.getSessionId());
      }
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasBaseResp()) {
        hash = (37 * hash) + BASERESP_FIELD_NUMBER;
        hash = (53 * hash) + getBaseResp().hashCode();
      }
      if (hasSessionId()) {
        hash = (37 * hash) + SESSIONID_FIELD_NUMBER;
        hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
            getSessionId());
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code protobuf.app.message.UpdateUserSignInStatusResp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:protobuf.app.message.UpdateUserSignInStatusResp)
        com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusRespOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.internal_static_protobuf_app_message_UpdateUserSignInStatusResp_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.internal_static_protobuf_app_message_UpdateUserSignInStatusResp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp.class, com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp.Builder.class);
      }

      // Construct using com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
          getBaseRespFieldBuilder();
        }
      }
      public Builder clear() {
        super.clear();
        if (baseRespBuilder_ == null) {
          baseResp_ = null;
        } else {
          baseRespBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        sessionId_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.internal_static_protobuf_app_message_UpdateUserSignInStatusResp_descriptor;
      }

      public com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp getDefaultInstanceForType() {
        return com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp.getDefaultInstance();
      }

      public com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp build() {
        com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp buildPartial() {
        com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp result = new com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        if (baseRespBuilder_ == null) {
          result.baseResp_ = baseResp_;
        } else {
          result.baseResp_ = baseRespBuilder_.build();
        }
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.sessionId_ = sessionId_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp) {
          return mergeFrom((com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp other) {
        if (other == com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp.getDefaultInstance()) return this;
        if (other.hasBaseResp()) {
          mergeBaseResp(other.getBaseResp());
        }
        if (other.hasSessionId()) {
          setSessionId(other.getSessionId());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        if (!hasBaseResp()) {
          return false;
        }
        if (!getBaseResp().isInitialized()) {
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.efun.game.protobuf.app.BaseRespProto.BaseResp baseResp_ = null;
      private com.google.protobuf.SingleFieldBuilderV3<
          com.efun.game.protobuf.app.BaseRespProto.BaseResp, com.efun.game.protobuf.app.BaseRespProto.BaseResp.Builder, com.efun.game.protobuf.app.BaseRespProto.BaseRespOrBuilder> baseRespBuilder_;
      /**
       * <pre>
       * 处理结果
       * </pre>
       *
       * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
       */
      public boolean hasBaseResp() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <pre>
       * 处理结果
       * </pre>
       *
       * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
       */
      public com.efun.game.protobuf.app.BaseRespProto.BaseResp getBaseResp() {
        if (baseRespBuilder_ == null) {
          return baseResp_ == null ? com.efun.game.protobuf.app.BaseRespProto.BaseResp.getDefaultInstance() : baseResp_;
        } else {
          return baseRespBuilder_.getMessage();
        }
      }
      /**
       * <pre>
       * 处理结果
       * </pre>
       *
       * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
       */
      public Builder setBaseResp(com.efun.game.protobuf.app.BaseRespProto.BaseResp value) {
        if (baseRespBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          baseResp_ = value;
          onChanged();
        } else {
          baseRespBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <pre>
       * 处理结果
       * </pre>
       *
       * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
       */
      public Builder setBaseResp(
          com.efun.game.protobuf.app.BaseRespProto.BaseResp.Builder builderForValue) {
        if (baseRespBuilder_ == null) {
          baseResp_ = builderForValue.build();
          onChanged();
        } else {
          baseRespBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <pre>
       * 处理结果
       * </pre>
       *
       * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
       */
      public Builder mergeBaseResp(com.efun.game.protobuf.app.BaseRespProto.BaseResp value) {
        if (baseRespBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001) &&
              baseResp_ != null &&
              baseResp_ != com.efun.game.protobuf.app.BaseRespProto.BaseResp.getDefaultInstance()) {
            baseResp_ =
              com.efun.game.protobuf.app.BaseRespProto.BaseResp.newBuilder(baseResp_).mergeFrom(value).buildPartial();
          } else {
            baseResp_ = value;
          }
          onChanged();
        } else {
          baseRespBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <pre>
       * 处理结果
       * </pre>
       *
       * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
       */
      public Builder clearBaseResp() {
        if (baseRespBuilder_ == null) {
          baseResp_ = null;
          onChanged();
        } else {
          baseRespBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      /**
       * <pre>
       * 处理结果
       * </pre>
       *
       * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
       */
      public com.efun.game.protobuf.app.BaseRespProto.BaseResp.Builder getBaseRespBuilder() {
        bitField0_ |= 0x00000001;
        onChanged();
        return getBaseRespFieldBuilder().getBuilder();
      }
      /**
       * <pre>
       * 处理结果
       * </pre>
       *
       * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
       */
      public com.efun.game.protobuf.app.BaseRespProto.BaseRespOrBuilder getBaseRespOrBuilder() {
        if (baseRespBuilder_ != null) {
          return baseRespBuilder_.getMessageOrBuilder();
        } else {
          return baseResp_ == null ?
              com.efun.game.protobuf.app.BaseRespProto.BaseResp.getDefaultInstance() : baseResp_;
        }
      }
      /**
       * <pre>
       * 处理结果
       * </pre>
       *
       * <code>required .protobuf.app.BaseResp baseResp = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          com.efun.game.protobuf.app.BaseRespProto.BaseResp, com.efun.game.protobuf.app.BaseRespProto.BaseResp.Builder, com.efun.game.protobuf.app.BaseRespProto.BaseRespOrBuilder> 
          getBaseRespFieldBuilder() {
        if (baseRespBuilder_ == null) {
          baseRespBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              com.efun.game.protobuf.app.BaseRespProto.BaseResp, com.efun.game.protobuf.app.BaseRespProto.BaseResp.Builder, com.efun.game.protobuf.app.BaseRespProto.BaseRespOrBuilder>(
                  getBaseResp(),
                  getParentForChildren(),
                  isClean());
          baseResp_ = null;
        }
        return baseRespBuilder_;
      }

      private long sessionId_ ;
      /**
       * <code>optional fixed64 sessionId = 2;</code>
       */
      public boolean hasSessionId() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional fixed64 sessionId = 2;</code>
       */
      public long getSessionId() {
        return sessionId_;
      }
      /**
       * <code>optional fixed64 sessionId = 2;</code>
       */
      public Builder setSessionId(long value) {
        bitField0_ |= 0x00000002;
        sessionId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional fixed64 sessionId = 2;</code>
       */
      public Builder clearSessionId() {
        bitField0_ = (bitField0_ & ~0x00000002);
        sessionId_ = 0L;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:protobuf.app.message.UpdateUserSignInStatusResp)
    }

    // @@protoc_insertion_point(class_scope:protobuf.app.message.UpdateUserSignInStatusResp)
    private static final com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp();
    }

    public static com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<UpdateUserSignInStatusResp>
        PARSER = new com.google.protobuf.AbstractParser<UpdateUserSignInStatusResp>() {
      public UpdateUserSignInStatusResp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new UpdateUserSignInStatusResp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<UpdateUserSignInStatusResp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<UpdateUserSignInStatusResp> getParserForType() {
      return PARSER;
    }

    public com.efun.game.protobuf.app.message.UpdateUserSignInStatusRespProto.UpdateUserSignInStatusResp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protobuf_app_message_UpdateUserSignInStatusResp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protobuf_app_message_UpdateUserSignInStatusResp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n(message/UpdateUserSignInStatusResp.pro" +
      "to\022\024protobuf.app.message\032\016BaseResp.proto" +
      "\"Y\n\032UpdateUserSignInStatusResp\022(\n\010baseRe" +
      "sp\030\001 \002(\0132\026.protobuf.app.BaseResp\022\021\n\tsess" +
      "ionId\030\002 \001(\006BE\n\"com.efun.game.protobuf.ap" +
      "p.messageB\037UpdateUserSignInStatusRespPro" +
      "to"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.efun.game.protobuf.app.BaseRespProto.getDescriptor(),
        }, assigner);
    internal_static_protobuf_app_message_UpdateUserSignInStatusResp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protobuf_app_message_UpdateUserSignInStatusResp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protobuf_app_message_UpdateUserSignInStatusResp_descriptor,
        new java.lang.String[] { "BaseResp", "SessionId", });
    com.efun.game.protobuf.app.BaseRespProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
