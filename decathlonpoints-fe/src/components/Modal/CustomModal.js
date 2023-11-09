import React, { useState } from 'react';
import { Button, Form, Input, InputNumber, Modal } from 'antd';
import DecathlonService from '../../service/decathlon.service';
import { CloseCircleOutlined, SaveOutlined } from "@ant-design/icons";
import './CustomModal.css';

function CustomModal( { callback } ) {
    const [ validationError, setValidationError ] = useState( {
        basePoints: '',
        resultMultiplier: '',
        resultExponent: ''
    } );
    const [ form ] = Form.useForm();

    const handleOk = () => {
        form.validateFields().then(() => {
            saveEvent();
            setTimeout(() => {
                callback();
            }, 200);
        }).catch(() => {
            console.log("Invalid form");
        });
    };

    const saveEvent = () => {
        form.submit();
    };
    const onFinish = ( values ) => {
        DecathlonService.create( values )
            .then( response => {
                console.log( response.data );
            } )
            .catch( e => {
                console.log( e );
            } );
    };
    const validateNumber = ( rule, value, param ) => {
        return new Promise( ( resolve, reject ) => {
            const validation = { ...validationError };
            if ( isNaN( value ) ) {
                validation[ param ] = "Please enter a valid number";
                setValidationError( validation );
                reject( 'Please enter a valid number' );
            } else if ( value <= 0 ) {
                validation[ param ] = `${ param } should be higher than zero`;
                setValidationError( validation );
                reject( `${ param } should be higher than zero` );
            } else {
                validation[ param ] = '';
                setValidationError( validation );
                resolve();
            }
        } );
    };
    const onFinishFailed = ( errorInfo ) => {
        console.log( 'Form submission failed:', errorInfo );
    };

    return (
        <div>
            <Modal title="Add Sport" onOk={ handleOk } onCancel={ callback } open={ true } okText="Save"
                   cancelText="Close" className="centered-modal" footer={ [
                <Button key="back" onClick={ callback } danger>
                    <CloseCircleOutlined/>
                    Close
                </Button>,
                <Button key="save" className="green-button" type="primary" onClick={ handleOk }>
                    <SaveOutlined/>
                    Save
                </Button>
            ] }>
                <Form
                    name="basic"
                    labelCol={ { span: 8 } }
                    wrapperCol={ { span: 16 } }
                    initialValues={ { remember: false } }
                    onFinish={ onFinish }
                    onFinishFailed={ onFinishFailed }
                    autoComplete="off"
                    form={ form }
                >
                    <Form.Item
                        label="Name"
                        name="eventName"
                        rules={ [ { required: true, message: 'Please input name!' } ] }
                    >
                        <Input
                            name="eventName"
                        />
                    </Form.Item>

                    <Form.Item
                        label="Base Points"
                        name="basePoints"
                        rules={ [
                            { required: true },
                            {
                                validator: ( rule, value ) => {
                                    return validateNumber( rule, value, "basePoints" )
                                }
                            }
                        ] }
                        validateStatus={ validationError.basePoints ? 'error' : '' }
                        help={ validationError.basePoints ? validationError.basePoints : '' }
                    >

                        <InputNumber style={ { width: "100%" } }
                                     name="basePoints"
                        />
                    </Form.Item>

                    <Form.Item
                        label="Result Multiplier"
                        name="resultMultiplier"
                        rules={ [
                            { required: true },
                            {
                                validator: ( rule, value ) => {
                                    return validateNumber( rule, value, "resultMultiplier" )
                                }
                            }
                        ] }
                        validateStatus={ validationError.resultMultiplier ? 'error' : '' }
                        help={ validationError.resultMultiplier ? validationError.resultMultiplier : '' }
                    >

                        <InputNumber style={ { width: "100%" } }
                                     name="resultMultiplier"
                        />

                    </Form.Item>

                    <Form.Item
                        label="Result Exponent"
                        name="resultExponent"
                        rules={ [
                            { required: true },
                            {
                                validator: ( rule, value ) => {
                                    return validateNumber( rule, value, "resultExponent" )
                                }
                            }
                        ] }
                        validateStatus={ validationError.resultExponent ? 'error' : '' }
                        help={ validationError.resultExponent ? validationError.resultExponent : '' }
                    >
                        <InputNumber style={ { width: "100%" } }
                                     name="resultExponent"
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
}

export default CustomModal;