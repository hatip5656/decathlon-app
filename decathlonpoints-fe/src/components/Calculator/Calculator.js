import React, { useState } from 'react';
import { Content, Header } from "antd/es/layout/layout";
import { Alert, Button, Col, Form, Grid, InputNumber, Layout, Row, Select } from "antd";
import { Option } from 'rc-select';
import './Calculator.css';
import DecathlonService from "../../service/decathlon.service";
import CustomModal from "../Modal/CustomModal";
import { CalculatorOutlined, PlusCircleOutlined } from "@ant-design/icons";

function Calculator() {
    const [ events, setEvents ] = useState( [] )
    const [ form ] = Form.useForm();
    const [ scoreError, setScoreError ] = useState( false );
    const [ modal, setModal ] = useState( false );
    const [ calculation, setCalculation ] = useState();
    const [ calculationError, setCalculationError ] = useState( { error: '', errorState: false } );

    React.useEffect( () => {
        getEvents();
    }, [] );
    const getEvents = () => {
        DecathlonService.getAll()
            .then( response => {
                setEvents( response.data );
            } )
            .catch( e => {
                console.log( e );
            } );
    }

    const onEventNameChange = ( value ) => {
        form.setFieldsValue( { eventName: value } );
    };

    const onFinish = ( values ) => {
        setCalculationError( { error: '', errorState: false } );
        setCalculation( null );
        DecathlonService.calculate( values ).then( response => {
            setCalculation( response.data );
        } ).catch( ( e ) => {
            setCalculationError( { error: e.response.data.errorMessage, errorState: true } )
        } );
    };
    const validateScore = ( rule, value ) => {
        if ( isNaN( value ) ) {
            setScoreError( 'Please enter a valid number' );
            return Promise.reject( 'Please enter a valid number' );
        } else if ( value <= 0 ) {
            setScoreError( 'Score should be higher then zero' );
            return Promise.reject( 'Score should be higher then zero' );
        } else {
            setScoreError( false );
            return Promise.resolve();
        }
    };

    const modalCallBack = () => {
        getEvents();
        setModal( false );
    }
    const screens = Grid.useBreakpoint();
    return (
        <Layout style={ { height: "100vh" } }>
            <Header className="header">
                Decathlon Point Calculator
            </Header>
            <Content className="content">
                <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
                    <Col className="gutter-row" span={ 16 } offset={ screens.md|| screens.xl || screens.lg ? 8 : 0 } md={24} xl={18} xs={24} sm={24}>
                        <Form
                            form={ form }
                            name="control-ref"
                            onFinish={ onFinish }
                            style={ { maxWidth: 600 } }
                            labelCol={ { span: 8 } } // Set the label width
                            wrapperCol={ { span: 16 } }
                        >
                            <Form.Item name="eventName" label="Sport" rules={ [ { required: true } ] }>
                                <Select
                                    placeholder="Select event name to calculate"
                                    onChange={ onEventNameChange }
                                    allowClear
                                >
                                    { events.map( ( event ) => (
                                        <Option key={ event.eventName } value={ event.eventName }>
                                            { event.eventName }
                                        </Option> ))}
                                </Select>
                            </Form.Item>
                            <Form.Item
                                name="score"
                                label="Score"
                                rules={ [
                                    { required: true },
                                    { validator: validateScore }
                                ] }
                                validateStatus={ scoreError ? 'error' : '' }
                                help={ scoreError ? scoreError : '' }
                            >
                                <InputNumber placeholder="Enter the score !" style={ { width: "100%" } }/>
                            </Form.Item>
                            <Form.Item wrapperCol={ {
                                xs: { span: 24 },
                                sm: { offset:9, span: 13 },
                                md: { offset: 8, span: 16 },
                                lg: { offset: 11, span: 16 },
                                xl: { offset: 11, span: 16 }
                            } }>
                                <Button type="primary" htmlType="submit" style={ { margin: "5px 5px 5px 5px" } }>
                                    <CalculatorOutlined/>
                                    Calculate
                                </Button>
                                <Button type="primary" className="green-button"
                                        style={ { margin: "5px 5px 5px 5px" } }
                                        onClick={ () => setModal( true ) }>
                                    <PlusCircleOutlined/>
                                    Add Sport
                                </Button>
                            </Form.Item>
                        </Form>
                    </Col>
                </Row>
                { modal && <CustomModal callback={ modalCallBack }/> }
                <Row gutter={ { xs: 32, sm: 32, md: 12, lg: 6 } }>
                    <Col className="gutter-row" span={ 12 } offset={ 6 }>
                        { calculationError.errorState && (
                            <Alert
                                style={ { textAlign: "center" } }
                                message={ calculationError.error }
                                type="error"
                            />
                        ) }
                        { !calculationError.errorState && calculation && (
                            <Alert
                                style={ { textAlign: "center" } }
                                message={ calculation }
                                type="success"
                            />
                        ) }
                    </Col>
                </Row>
            </Content>
        </Layout>
    );

}

export default Calculator;
